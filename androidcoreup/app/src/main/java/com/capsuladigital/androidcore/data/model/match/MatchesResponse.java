package com.capsuladigital.androidcore.data.model.match;

import java.util.List;

public class MatchesResponse {
    private List<Match> matches;

    public MatchesResponse(List<Match> matches) {
        this.matches = matches;
    }

    public List<Match> getMatches() {
        return matches;
    }
}
