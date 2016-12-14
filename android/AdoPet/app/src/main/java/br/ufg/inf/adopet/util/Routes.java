package br.ufg.inf.adopet.util;

/**
 * Created by root on 14/12/16.
 */

public interface Routes {

    String BASE_URL = "https://api-adopet.herokuapp.com/";
    int MAX_TIMEOUT_MS = 30000;
    String ANIMALS = BASE_URL + "animals";
    String ANIMAL = BASE_URL + "animals/";

    interface GET {
        String POST = BASE_URL + "post/";
    }
}
