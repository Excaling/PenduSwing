package game;

import java.io.File;

public class Pendu {

	public int chance;
	private String secretWord;
	private char[] maskWord;
	public boolean win;
	public Pendu() {

		this.secretWord = "";
		maskWord = new char[this.secretWord.length()];
		for(int i = 0; i < maskWord.length; i++) {
			maskWord[i] = '*';
		}
		win = false;
		
	}
	public Pendu(int pChance, String pWord) {
		chance = pChance; 
		this.secretWord = pWord;
		maskWord = new char[this.secretWord.length()];
		for(int i = 0; i < maskWord.length; i++) {
			maskWord[i] = '*';
		}
		win = false;
	}
	
	public String getSecretWord() {
		return secretWord;
	}
	
	public char[] getMaskWord() {
		char[] mask = new char[this.maskWord.length];
		for(int i = 0; i<mask.length; i++) {
			mask[i] = this.maskWord[i];
		}
		return mask;
	}
	
	public void toTry(char word) {
		char[] secret = this.secretWord.toCharArray();
		for(int i = 0; i < secret.length; i ++) {
			if(secret[i] ==  word) {
				this.maskWord[i] = word; 
			}
		}
		int j = 0;
		boolean equals = true;
		while(j < this.maskWord.length && equals) {
			if(this.maskWord[j] != secret[j]) { equals = false; win = false;}
			j++;
		}
		win = equals;
		chance--;
	}
	
	public void displaySecret() {
		System.out.print("secret Word: ");
		for(int i = 0; i < this.maskWord.length; i++) {
			System.out.print(this.maskWord[i]);
		}
		System.out.println("");
	}
	
}
