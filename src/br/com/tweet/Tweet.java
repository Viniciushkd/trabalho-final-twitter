package br.com.tweet;

import java.util.Date;

public class Tweet implements Comparable<Tweet>{

	private String nome;
	private Date data;
	private String tweet;
	private boolean isReTweet;
	
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
	public boolean isReTweet() {
		return isReTweet;
	}
	public void setReTweet(boolean isReTweet) {
		this.isReTweet = isReTweet;
	}
	
	@Override
	public int compareTo(Tweet arg0) {
		return this.nome.compareTo(arg0.nome);
	}
	
	@Override
	public String toString() {
		return getNome();
	}
	
}
