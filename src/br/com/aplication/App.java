package br.com.aplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.com.tweet.ControleTweet;
import br.com.tweet.Tweet;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class App {

	public static void main(String[] args) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime dateNow = LocalDateTime.now();
		Twitter twitter = TwitterFactory.getSingleton();
		
		HashMap<String , ControleTweet> hm = new HashMap<>();
		
		for(int i=7;i>0;i--) {
			LocalDateTime dateLast = LocalDateTime.of(dateNow.getYear(), dateNow.getMonth(), dateNow.getDayOfMonth() - i-1, dateNow.getHour(), dateNow.getMinute());
			LocalDateTime dateAfter = LocalDateTime.of(dateNow.getYear(), dateNow.getMonth(), dateNow.getDayOfMonth() - i, dateNow.getHour(), dateNow.getMinute());
			String dateLastFormatt = dateLast.format(dtf);
			String dateAfterFormatt = dateAfter.format(dtf);
			
			hm.put(dateAfterFormatt, buscaTwitter("#java9 since:" + dateLastFormatt + " until:" + dateAfterFormatt, twitter));
		}
		
		for(String i : hm.keySet()) {
			String  obs = hm.get(i).getQuantidade() == 99 ? "Obs: Pode ter sido mais que 99" : "";
			System.out.println("Dia " + i + " teve " + hm.get(i).getQuantidade() + " tweets " + obs);
		}
		
		for(ControleTweet controle : hm.values()) {
			int reTweet = 0;
			for(Tweet c : controle.getListTweet()) {
				if(c.isReTweet()) {
					reTweet++;
				}
			}
			System.out.println(hm.keySet().iterator().next()+" - Quantidade de ReTweet: " + reTweet);
		}
		System.out.println("");
		
		for(ControleTweet controle : hm.values()) {
			List<Tweet> listaTweets = controle.getListTweet();
			Collections.sort(listaTweets);
			System.out.println(listaTweets);
			System.out.println(listaTweets.stream().findFirst().get().getNome());
			System.out.println(listaTweets.get(listaTweets.size()-1)+"\n");
		}
	}

	private static ControleTweet buscaTwitter(String busca, Twitter twitter) {
		try {
			ControleTweet controle = new ControleTweet();
			Query query = new Query(busca);
			query.setCount(150);
			QueryResult result = twitter.search(query);
			List<Tweet> tweets  = new ArrayList<>();

			for (Status status : result.getTweets()) {
				Tweet tweet = new Tweet();
				tweet.setNome(status.getUser().getName());
				tweet.setData(status.getCreatedAt());
				tweet.setTweet(status.getText());
				tweet.setReTweet(status.isRetweet() ? true : false);
				tweets.add(tweet);
			}
			
			controle.setListTweet(tweets);
			controle.setQuantidade(result.getTweets().size());
			
			return controle;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
