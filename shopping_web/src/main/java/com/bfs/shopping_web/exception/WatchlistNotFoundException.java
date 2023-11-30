package com.bfs.shopping_web.exception;

public class WatchlistNotFoundException extends Exception{
    public WatchlistNotFoundException() {
        super("Watchlist not found");
    }

}
