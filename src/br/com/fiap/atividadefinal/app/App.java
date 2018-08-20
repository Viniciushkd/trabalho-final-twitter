package br.com.fiap.atividadefinal.app;

import br.com.fiap.atividadefinal.service.GetTwitterAPI;
import br.com.fiap.atividadefinal.service.PostTwitterAPI;

public class App {

	public static void main(String[] args) {
		GetTwitterAPI getTwitterAPI = new GetTwitterAPI();

		var resultado = getTwitterAPI.doGet("#java9");

		PostTwitterAPI postTwitterAPI = new PostTwitterAPI();

		postTwitterAPI.doPost(resultado);
	}

}
