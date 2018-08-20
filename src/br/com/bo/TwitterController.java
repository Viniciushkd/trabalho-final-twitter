package br.com.bo;

import java.util.ArrayList;
import java.util.List;

import br.com.tweet.ControleTweet;
import br.com.tweet.Tweet;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterController {

	public ControleTweet buscaTwitter(String busca, Twitter twitter) {
		try {
			ControleTweet controle = new ControleTweet();
			Query query = new Query(busca);
			query.setCount(150);
			QueryResult result = twitter.search(query);
			List<Tweet> tweets  = new ArrayList<>();

			int countReTweet = 0, countFavorited = 0;
			for (Status status : result.getTweets()) {
				Tweet tweet = new Tweet();
				tweet.setNome(status.getUser().getName());
				tweet.setData(status.getCreatedAt());
				tweet.setTweet(status.getText());
				countReTweet = status.isRetweet() ? countReTweet + 1 : countReTweet + 0;
				countFavorited = status.isFavorited() ? countFavorited + 1 : countFavorited + 0; // ? Retorna tudo falso
				tweets.add(tweet);
			}
			
			controle.setListTweet(tweets);
			controle.setQuantidade(result.getTweets().size());
			controle.setQuantidadeReTweet(countReTweet);
			controle.setQuantidadeFavorited(countFavorited);
			
			return controle;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void postTweet(String texto, Twitter twitter) throws TwitterException {
	    Status status = twitter.updateStatus(texto);
	    System.out.println("Post efetuado com sucesso [" + status.getText() + "].");
	}
}
