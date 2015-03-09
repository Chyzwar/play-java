package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VarnishSearch {

	private final String urlBase = "http://vcache.arnoldclark.com/imageserver/";
	private final List<String> pictureSizes;
	private final List<String> picturesSuffix;
	private final int maxPictures = 12;
	private ArrayList<String> results;

	private String stockRef;
	private String registration;
	private String obfuscatedRef;

	/**
	 * Constructor
	 * @param  String 	stock_ref
	 * @param  String registration
	 */
	public VarnishSearch(String stockRef, String registration) {
		this.stockRef = stockRef;
		this.registration = registration;

		this.picturesSuffix =   Arrays.asList("f", "r", "i");
		this.pictureSizes =  Arrays.asList("350", "800");
		this.results = new ArrayList();
	}

	/**
	 * Return usefull repesenation
	 * @return String
	 */
	public String toString() {
		return "stock_ref: " + this.stockRef + "registration: " + this.registration;
	}

	/**
	 * Find a urls to Varnish cache images;
	 * @return ArrayList results
	 */
	public ArrayList<String> createUrlList() {

		this.obfuscatedRef = this.getObfucatedRef();

		if (!this.obfuscatedRef.isEmpty()) {
			this.populateUrlResults();
		} else {
			this.results.add("Wrong registration or stock refefrence");
		}
		return this.results;
	}

	/**
	 * Obfuscated stock reference is calculated by interleaving the stock reference with the
	 * reversal of the registration plate and appending the 9th character of the stock reference and
	 * taking the shortest possible solution (i.e. ignore any unused characters from the stock	reference
	 * @return  String
	 */
	private String getObfucatedRef() {

		String obfuscatedRef = ""; 
		int stockLen = this.stockRef.length();
		int regLen = this.registration.length();

		if (regLen > 0 && stockLen > 8) {
			for (int i = 0; i < regLen; i++ ) {
				obfuscatedRef += this.stockRef.charAt(i);
				obfuscatedRef += this.registration.charAt((regLen - 1) - i);
			}
			obfuscatedRef += stockRef.charAt(8);
		}
		return obfuscatedRef;
	}

	/**
	 * Pupulate Results Array List 
	 * This is still Ugly
	 */
	private void populateUrlResults() {

		for (int i = 0; i < this.maxPictures; i++) {
			if (i < this.picturesSuffix.size()) {
				this.results.add(this.buildUrl(0) + this.picturesSuffix.get(i));
				this.results.add(this.buildUrl(1) + this.picturesSuffix.get(i));
			} else {
				this.results.add(this.buildUrl(0) + i);
				this.results.add(this.buildUrl(1) + i);
			}
		}
	}

	/**
	 * Build up a url String
	 * @param  int size
	 * @return  String partial url
	 */
	private String buildUrl(int size) {

		String url = this.urlBase + this.obfuscatedRef + "/" + this.pictureSizes.get(size) + "/";

		return url;
	}


}