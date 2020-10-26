package com.adriel.desafiointer.api.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheLRU<K,V> extends LinkedHashMap<K,V> {
	

	  private static final long serialVersionUID = -2423777295355878132L;
	  
	  private final int numeroMaxValores;
	  private static final int DEFAULT_INITIAL_CAPACITY = 16;
	  private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	  public CacheLRU(int initialCapacity,
	                  float loadFactor,
	                  int numeroMaxValores) {
	    super(initialCapacity, loadFactor, true);
	    this.numeroMaxValores = numeroMaxValores;
	  }

	  public CacheLRU(int initialCapacity,
	                  int numeroMaxValores) {
	    this(initialCapacity, DEFAULT_LOAD_FACTOR, numeroMaxValores);
	  }

	  public CacheLRU(int numeroMaxValores) {
	    this(DEFAULT_INITIAL_CAPACITY, numeroMaxValores);
	  }

	  // not very useful constructor
	  public CacheLRU(Map<? extends K, ? extends V> m,
	                  int numeroMaxValores) {
	    this(m.size(), numeroMaxValores);
	    putAll(m);
	  }

	  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
	    return size() > numeroMaxValores;
	  }

}
