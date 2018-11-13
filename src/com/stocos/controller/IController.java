package com.stocos.controller;

public interface IController {

	void handle() throws Exception;

	void get(String path, Rota callback) throws Exception;

	void put(String path, Rota callback) throws Exception;

	void post(String path, Rota callback) throws Exception;

	void delete(String path, Rota callback) throws Exception;

}
