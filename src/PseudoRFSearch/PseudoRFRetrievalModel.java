package PseudoRFSearch;

import java.util.*;

import Classes.MovieDoc;
import Classes.Query;
import IndexingLucene.MyIndexReader;
import SearchLucene.*;
//import Search.*;

public class PseudoRFRetrievalModel{

	MyIndexReader ixreader;
	//The Dirichlet Prior Smoothing factor
	private double MIU = 500.0;
	// set collection size
	private long COLLECTION_SIZE;
	// store the result of query with the format: [docid, [term, term_freq]]
	private Map<Integer, HashMap<String, Integer>> query_Result;
	// store the collection_frep to compute Dirichlet Prior Smoothing
	private Map<String, Long> collect_termFreq;
	
	public PseudoRFRetrievalModel(MyIndexReader ixreader) throws Exception
	{
		this.ixreader=ixreader;
		// get collection size
		COLLECTION_SIZE = this.ixreader.collect_length();
	}
	
	/**
	 * Search for the topic with pseudo relevance feedback in 2017 spring assignment 4. 
	 * The returned results (retrieved documents) should be ranked by the score (from the most relevant to the least).
	 * 
	 * @param aQuery The query to be searched for.
	 * @param TopN The maximum number of returned document
	 * @param TopK The count of feedback documents
	 * @param alpha parameter of relevance feedback model
	 * @return TopN most relevant document, in List structure
	 */
	public List<MovieDoc> RetrieveQuery( String aQuery, int TopN, int TopK, double alpha) throws Exception {
		// this method will return the retrieval result of the given Query, and this result is enhanced with pseudo relevance feedback
		// (1) you should first use the original retrieval model to get TopK documents, which will be regarded as feedback documents
		// (2) implement GetTokenRFScore to get each query token's P(token|feedback model) in feedback documents
		// (3) implement the relevance feedback model for each token: combine the each query token's original retrieval score P(token|document) with its score in feedback documents P(token|feedback model)
		// (4) for each document, use the query likelihood language model to get the whole query's new score, P(Q|document)=P(token_1|document')*P(token_2|document')*...*P(token_n|document')

		//get P(token|feedback documents)
		HashMap<String,Double> TokenRFScore=GetTokenRFScore(aQuery,TopK);
		
		
		// sort all retrieved documents from most relevant to least, and return TopN
		List<MovieDoc> results = new ArrayList<MovieDoc>();
		// get retrieval doc score
		// store tokens in aQuery into String array
		String[] tokens = aQuery.split(" ");

		// query likelihood model, calculate the probability of
		// each document model generating each query terms
		// then multiply all probability to get score
		// store query likelihood model result [docid, score]
		HashMap<Integer,Double> doc_score = new HashMap<>();
		query_Result.forEach((docid, term_tf) -> {
			int doclen = 0;
			double score = 1.0;
			try {
				doclen = ixreader.docLength(docid);
			} catch(Exception e) {};
			// Dirichlet piror smoothing
			// p(w|D) = (c(w,D) + MIU*p(w|REF) ) / (|D| + MIU)
			// score  = mulitiply(p(w|D))
			for (String token : tokens) {
				long ctf = collect_termFreq.get(token);
				if (ctf == 0) continue;
				int tf = term_tf.getOrDefault(token, 0); //c(w|D)
				double p_ref = (double)ctf / COLLECTION_SIZE; // p(w|REF)
				score *= alpha * (((double)tf + MIU*p_ref) / ((double)doclen + MIU)) + (1-alpha)*TokenRFScore.get(token);
			}
			doc_score.put(docid,score);
		});

		//sort doc_score by score
		List<Map.Entry<Integer,Double>> list_doc_score = new ArrayList<Map.Entry<Integer,Double>>(doc_score.entrySet());
		Collections.sort(list_doc_score,new Comparator<Map.Entry<Integer,Double>>(){
			@Override
			public int compare(Map.Entry<Integer, Double> o1,
							   Map.Entry<Integer, Double> o2) {
				int flag = o2.getValue().compareTo(o1.getValue());
				if(flag==0){
					return o2.getKey().compareTo(o1.getKey());
				}
				return flag;
			}
		});

		// put all documents into result list
		for (int rank = 0; rank < doc_score.size(); rank++) {
			MovieDoc doc = null;
			Map.Entry<Integer, Double> item = list_doc_score.get(rank);
			try {
				int id = item.getKey();
				//doc = new MovieDoc(Integer.toString(id), ixreader.getDocno(id),ixreader.DoContent(id), item.getValue());
			} catch(Exception e) {};
			results.add(doc);
		}

		return results;
	}
	
	public HashMap<String,Double> GetTokenRFScore(String aQuery,  int TopK) throws Exception
	{
		// for each token in the query, you should calculate token's score in feedback documents: P(token|feedback documents)
		// use Dirichlet smoothing
		// save <token, score> in HashMap TokenRFScore, and return it
		HashMap<String,Double> TokenRFScore=new HashMap<String,Double>();

		// store tokens in aQuery into String array
		String[] tokens = aQuery.split(" ");
		// get feedback documents

		List<MovieDoc> feedbackDoc = new QueryRetrievalModel(ixreader).retrieveQuery(aQuery, TopK);


		collect_termFreq = new HashMap<>();
		query_Result = new HashMap<>();

		// search for each token
		for (String token:tokens){
			//get the total number of times the token appears in the collection.
			long ctf = ixreader.CollectionFreq(token);
			collect_termFreq.put(token, ctf);
			if (ctf == 0) {
				System.out.println(token + " is not found in the collection!");
				continue;
			}

			int[][] postingList = ixreader.getPostingList(token);
			for(int[] posting : postingList) {
				if(!query_Result.containsKey(posting[0])) {
					HashMap<String, Integer> temp = new HashMap<>();
					temp.put(token, posting[1]);
					query_Result.put(posting[0], temp);
				}
				else
					query_Result.get(posting[0]).put(token, posting[1]);
			}
		}

		//all feedback documents are treated as one big pseudo document
		//pseudo document stores token and its term frequency
		Map<String, Integer> pseudo_Doc = new HashMap<>();
		int doclen = 0;
		for (MovieDoc doc:feedbackDoc){
			query_Result.get(Integer.parseInt(doc.getId())).forEach((term,freq)->{
				if (pseudo_Doc.containsKey(term)){
					pseudo_Doc.put(term,freq + pseudo_Doc.get(term));
				}
				else pseudo_Doc.put(term,freq);
			});
			doclen += ixreader.docLength(Integer.parseInt(doc.getId()));
		}

		//calculate token's score in feedback documents
		final int doc_len = doclen;
		pseudo_Doc.forEach((token, freq) -> {
			double score = 1.0;
			// Dirichlet piror smoothing
			// p(w|D) = (c(w,D) + MIU*p(w|REF) ) / (|D| + MIU)
			// c(w,D) = freq
			// score  = mulitiply(p(w|D))
			long ctf = collect_termFreq.get(token);

			double p_ref = (double)ctf / COLLECTION_SIZE; // p(w|REF)
			score = ((double)freq + MIU*p_ref) / ((double)doc_len + MIU);
			TokenRFScore.put(token,score);
		});

		return TokenRFScore;
	}
	
	
}