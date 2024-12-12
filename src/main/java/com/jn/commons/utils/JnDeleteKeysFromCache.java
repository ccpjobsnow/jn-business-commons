package com.jn.commons.utils;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.cache.CcpCacheDecorator;

public class JnDeleteKeysFromCache implements Function<CcpJsonRepresentation, CcpJsonRepresentation>, Consumer<String[]> {

	public static final JnDeleteKeysFromCache INSTANCE = new JnDeleteKeysFromCache();
	private static final String KEYS_TO_DELETE_IN_CACHE = "keysToDeleteInCache";
	
	private JnDeleteKeysFromCache() {}
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		
		Collection<String> allCacheKeys = json.getAsStringList(KEYS_TO_DELETE_IN_CACHE);
		
		for (String cacheKey : allCacheKeys) {
			CcpCacheDecorator cache = new CcpCacheDecorator(cacheKey);
			cache.delete();
		}
		return json;
	}

	public void accept(String[] keysToDeleteInCache) {
		CcpJsonRepresentation json = CcpConstants.EMPTY_JSON.put(KEYS_TO_DELETE_IN_CACHE, keysToDeleteInCache);
		this.apply(json);
		
	}

}
