package br.com.lorrandev.screen_match.service;

public interface ConvertData {
    <T> T getData(String json, Class<T> classe);
 }
