package br.com.fiap.atividadefinal.vo;

import java.util.Date;

public class TweetVO implements Comparable<TweetVO> {

	private long id;
	private String nome;
	private Date data;
	private String tweet;
	private boolean retweet;
	private boolean favorited;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public boolean isRetweet() {
		return retweet;
	}

	public void setRetweet(boolean retweet) {
		this.retweet = retweet;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TweetVO other = (TweetVO) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(TweetVO tweet) {
		return Long.compare(id, tweet.getId());
	}

	@Override
	public String toString() {
		return getNome();
	}

}
