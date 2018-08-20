package br.com.fiap.atividadefinal.bo;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.com.fiap.atividadefinal.vo.ControleTweetVO;
import br.com.fiap.atividadefinal.vo.TweetVO;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class PostTwitterAPI {

	private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private Twitter twitter;

	public PostTwitterAPI() {
		twitter = TwitterFactory.getSingleton();
	}

	public void doPost(Map<LocalDateTime, ControleTweetVO> resultado) {
		List<TweetVO> tweets = new ArrayList<>();

		StringBuilder texto = new StringBuilder();

		int count = 0;
		for (LocalDateTime dia : resultado.keySet()) {
			ControleTweetVO controleTweet = resultado.get(dia);

			texto.append(dia.format(DATE_PATTERN)).append("\n");
			texto.append("QTD de tweets:      " + controleTweet.getQuantidade()).append("\n");
			texto.append("QTD de retweets:    " + controleTweet.getQuantidadeReTweet()).append("\n");
			texto.append("QTD de favoritados: " + controleTweet.getQuantidadeFavorited()).append("\n\n");

			count++;

			if (count == 3) {
				postTweet(texto.toString());

				texto = new StringBuilder();
				count = 0;
			}

			tweets.addAll(controleTweet.getListTweet());
		}

		Collections.sort(tweets, (u1, u2) -> u1.getNome().compareTo(u2.getNome()));

		texto.append("Primeiro nome: " + tweets.get(0)).append("\n");

		Collections.reverse(tweets);

		texto.append("Último nome: " + tweets.get(0)).append("\n");

		Collections.sort(tweets, (u1, u2) -> u1.getData().compareTo(u2.getData()));

		texto.append("Mais antigo: " + SDF.format(tweets.get(0).getData())).append("\n");

		Collections.reverse(tweets);

		texto.append("Mais recente: " + SDF.format(tweets.get(0).getData())).append("\n");

		postTweet(texto.toString());
	}

	private void postTweet(String texto) {
		try {
			Status status = twitter.updateStatus(texto);

			System.out.println("Post efetuado com sucesso [" + status.getText() + "].");
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

}
