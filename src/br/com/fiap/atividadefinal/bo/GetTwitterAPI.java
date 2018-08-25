package br.com.fiap.atividadefinal.bo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import br.com.fiap.atividadefinal.vo.ControleTweetVO;
import br.com.fiap.atividadefinal.vo.TweetVO;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class GetTwitterAPI {

	private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final DateTimeFormatter DATE_PATTERN_2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private String chave;

	private Twitter twitter;

	private Map<LocalDateTime, ControleTweetVO> resultado;

	public GetTwitterAPI() {
		twitter = TwitterFactory.getSingleton();
	}

	public Map<LocalDateTime, ControleTweetVO> doGet(String chave) {
		this.chave = chave;

		buscaTweetsUltimaSemana();

		return resultado;
	}

	private void buscaTweetsUltimaSemana() {
		LocalDateTime now = LocalDateTime.now();

		resultado = new TreeMap<>();

		for (int i = 7; i > 0; i--) {
			LocalDateTime since = now.minusDays(i);
			LocalDateTime until = now.minusDays(i - 1);

			List<TweetVO> tweets = buscaTweets(since, until);

			var controle = montaControle(tweets);

			StringBuilder sb = new StringBuilder();

			sb.append("Para o dia ").append(since.format(DATE_PATTERN_2)).append(" foram encontrados:\n");
			sb.append(controle.getQuantidade()).append(" tweets no total\n");
			sb.append(controle.getQuantidadeReTweet()).append(" foram retweets\n");
			sb.append(controle.getQuantidadeFavorited()).append(" foram favoritados\n");
			sb.append("Pressione Ok para continuar");

			JOptionPane.showMessageDialog(null, sb.toString(), "Twitter", JOptionPane.INFORMATION_MESSAGE);

			resultado.put(since, controle);
		}
	}

	private List<TweetVO> buscaTweets(LocalDateTime since, LocalDateTime until) {
		Query query = new Query(chave);

		query.count(150);
		query.setSince(since.format(DATE_PATTERN));
		query.setUntil(until.format(DATE_PATTERN));

		QueryResult result = null;

		List<TweetVO> tweets = new ArrayList<>();

		do {
			try {
				result = twitter.search(query);

				for (Status status : result.getTweets()) {
					TweetVO tweet = new TweetVO();

					tweet.setId(status.getId());
					tweet.setNome(status.getUser().getName());
					tweet.setData(status.getCreatedAt());
					tweet.setTweet(status.getText());
					tweet.setRetweet(status.isRetweet());
					tweet.setFavorited(status.isFavorited());

					tweets.add(tweet);
				}

				query = result.nextQuery();
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		} while (result.hasNext());

		return tweets;
	}

	private ControleTweetVO montaControle(List<TweetVO> tweets) {
		var controle = new ControleTweetVO();

		int countReTweet = 0, countFavorited = 0;
		for (TweetVO tweet : tweets) {
			if (tweet.isRetweet())
				countReTweet++;

			if (tweet.isFavorited())
				countFavorited++;
		}

		controle.setListTweet(tweets);
		controle.setQuantidade(tweets.size());
		controle.setQuantidadeReTweet(countReTweet);
		controle.setQuantidadeFavorited(countFavorited);

		return controle;
	}

}
