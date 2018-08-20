package br.com.fiap.atividadefinal.app;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.fiap.atividadefinal.bo.GetTwitterAPI;
import br.com.fiap.atividadefinal.bo.PostTwitterAPI;
import br.com.fiap.atividadefinal.vo.ControleTweetVO;

public class TwitterApplication {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		HashMap<String, Integer> hm = new HashMap<>();

		hm.put("Buscar pela hashtag...", 1);
		hm.put("Post Tweet", 2);

		Map<LocalDateTime, ControleTweetVO> resultado = null;

		while (true) {
			Object[] twitter = hm.keySet().toArray();

			String opcao = (String) JOptionPane.showInputDialog(null, "Opções", "Twitter", JOptionPane.DEFAULT_OPTION,
					null, twitter, null);

			if (opcao == null)
				break;

			switch (hm.get(opcao)) {
			case 1:
				String texto = JOptionPane.showInputDialog("Buscar pela hashtag:");
				texto = texto.startsWith("#") ? texto : "#" + texto;

				GetTwitterAPI getTwitterAPI = new GetTwitterAPI();

				resultado = getTwitterAPI.doGet(texto);
				break;

			case 2:
				if (resultado != null) {
					PostTwitterAPI postTwitterAPI = new PostTwitterAPI();

					postTwitterAPI.doPost(resultado);
				} else {
					JOptionPane.showMessageDialog(null, "Primeiro busque por uma hashtag!", "Erro!",
							JOptionPane.ERROR_MESSAGE);
				}
				break;
			}
		}
	}

}
