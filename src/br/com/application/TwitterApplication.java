package br.com.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.bo.TwitterController;
import br.com.tweet.ControleTweet;
import br.com.tweet.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterApplication {

	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final LocalDateTime dateNow = LocalDateTime.now();
	private static final Twitter twitter = TwitterFactory.getSingleton();
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		while (true) {
			HashMap<String, Integer> hm = new HashMap<>();
			hm.put("Buscar pela hashtag...", 1);
			hm.put("Post Tweet", 2);
			Object[] twitter = hm.keySet().toArray();
			
			String opcao = (String) JOptionPane.showInputDialog(null, "Opções", "Twitter", JOptionPane.DEFAULT_OPTION, null,
					twitter, null);
			
			if (opcao == null)
				break;
			
			switch (hm.get(opcao)) {
			case 1:
				String texto = JOptionPane.showInputDialog("Buscar pela hashtag:");
				buscaTweet(texto.replace("#", ""));
				break;

			case 2:
				postTweet();
				break;
			}
		}
	}
	
//  BUSCA TWEET
	private static void buscaTweet(String busca) {
		TwitterController twitterController = new TwitterController();
		HashMap<String , ControleTweet> hm = new HashMap<>();
		
		for(int i=7;i>0;i--) {
			LocalDateTime dateLast = LocalDateTime.of(dateNow.getYear(), dateNow.getMonth(), dateNow.getDayOfMonth() - i-1, dateNow.getHour(), dateNow.getMinute());
			LocalDateTime dateAfter = LocalDateTime.of(dateNow.getYear(), dateNow.getMonth(), dateNow.getDayOfMonth() - i, dateNow.getHour(), dateNow.getMinute());
			String dateLastFormatt = dateLast.format(dtf);
			String dateAfterFormatt = dateAfter.format(dtf);
			
			hm.put(dateAfterFormatt, twitterController.buscaTwitter("#" + busca + " since:" + dateLastFormatt + " until:" + dateAfterFormatt, twitter));
		}
		
		for(String i : hm.keySet()) {
			String  obs = hm.get(i).getQuantidade() == 99 ? "Obs: Pode ter sido mais que 99" : "";
			System.out.println("Dia " + i + " teve " + hm.get(i).getQuantidade() + " tweets " + obs);
		}

		System.out.println("");
		
		for(String i : hm.keySet()) {
			System.out.println("Dia " + i + " teve " + hm.get(i).getQuantidadeReTweet() + " retweets ");
		}
		
		System.out.println("");
		
		for(String i : hm.keySet()) {
			System.out.println("Dia " + i + " teve " + hm.get(i).getQuantidadeFavorited() + " favoritos ");
		}
		
		System.out.println("");
		
		for(String i : hm.keySet()) {
			List<Tweet> listaTweets = hm.get(i).getListTweet();
			System.out.println("Dia " + i);
			Collections.sort(listaTweets);
			System.out.println(listaTweets);
			System.out.println("Primeiro da lista: " + listaTweets.stream().findFirst().get().getNome());
			System.out.println("Ultimo da lista: " + listaTweets.get(listaTweets.size()-1)+"\n");
		}

	}

//	POST TWEET
	private static void postTweet() {
		TwitterController twitterController = new TwitterController();
		try {
			twitterController.postTweet("Trabalho Final Fundamento Plataforma Java e UML - @michelpf", twitter);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

}
