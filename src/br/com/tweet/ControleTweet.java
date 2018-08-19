package br.com.tweet;

import java.util.ArrayList;
import java.util.List;

public class ControleTweet {

	private List<Tweet> listTweet = new ArrayList<>();
	private int quantidade;
	private int quantidadeReTweet;
	private int quantidadeFavorited;
	
	public List<Tweet> getListTweet() {
		return listTweet;
	}
	public void setListTweet(List<Tweet> listTweet) {
		this.listTweet = listTweet;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getQuantidadeReTweet() {
		return quantidadeReTweet;
	}
	public void setQuantidadeReTweet(int quantidadeReTweet) {
		this.quantidadeReTweet = quantidadeReTweet;
	}
	public int getQuantidadeFavorited() {
		return quantidadeFavorited;
	}
	public void setQuantidadeFavorited(int quantidadeFavorited) {
		this.quantidadeFavorited = quantidadeFavorited;
	}
}
