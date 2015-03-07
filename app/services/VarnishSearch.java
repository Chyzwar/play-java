package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VarnishSearch {

	private final String urlBase = "http://vcache.arnoldclark.com/imageserver/";
	private final List<String> pictureSizes;
	private final List<String> picturesSuffix;
	private final int maxPictures = 12;

	private String stockRef;
	private String registration;

	/**
	 * Class Constructor
	 * @param  String 	stock_ref
	 * @param  String registration
	 */
	public VarnishSearch(String stockRef, String registration) {
		this.stockRef = stockRef;
		this.registration = registration;

		this.picturesSuffix =   Arrays.asList("f", "r", "i");
		this.pictureSizes =  Arrays.asList("350", "800");
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
	 * @return [description]
	 */
	public ArrayList<String> createUrlList() {

		ArrayList<String> results =  new ArrayList();
		String obfuscatedRef = this. getObfucatedRef();

		if (!obfuscatedRef.isEmpty()) {
			for (int i = 0; i < this.maxPictures; i++) {
				if (i < this.picturesSuffix.size()) {
					results.add(urlBase + obfuscatedRef + "/" + this.pictureSizes.get(0) + "/" + this.picturesSuffix.get(i));
					results.add(urlBase + obfuscatedRef + "/" + this.pictureSizes.get(1) + "/" + this.picturesSuffix.get(i));
				} else {
					results.add(urlBase + obfuscatedRef + "/" + this.pictureSizes.get(0) + "/" + i);
					results.add(urlBase + obfuscatedRef + "/" + this.pictureSizes.get(1) + "/" + i);
				}
			}
		} else {
			results.add("Wrong registration or stock_ref");
		}
		return results;
	}

	private String getObfucatedRef() {

		String obfuscatedRef="";

		int stockLen = this.stockRef.length();
		int regLen = this.registration.length();

		if (regLen > 1 && stockLen > 8) {
			for (int i = 0; i < regLen; i++ ) {
				obfuscatedRef += this.stockRef.charAt(i);
				obfuscatedRef += this.registration.charAt((regLen-1) - i);
			}
			obfuscatedRef += stockRef.charAt(8);
		}

		return obfuscatedRef;
	}


}