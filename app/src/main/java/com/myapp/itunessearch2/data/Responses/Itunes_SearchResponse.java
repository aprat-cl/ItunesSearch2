package com.myapp.itunessearch2.data.Responses;

public class Itunes_SearchResponse {
    public String
            wrapperType,
            kind,
            artistName,
            collectionName,
            trackName,
            collectionCensoredName,
            trackCensoredName,
            artistViewUrl,
            collectionViewUrl,
            trackViewUrl,
            previewUrl,
            artworkUrl30,
            artworkUrl60,
            artworkUrl100,
            collectionExplicitness,
            trackExplicitness,
            country,
            currency,
            primaryGenreName,
            releaseDate;
    public int
            discCount,
            discNumber,
            trackCount,
            trackNumber;
    public long
            artistId,
            collectionId,
            trackId,
            trackTimeMillis;
    public float
            collectionPrice,
            trackPrice;
    public boolean isStreamable;
}