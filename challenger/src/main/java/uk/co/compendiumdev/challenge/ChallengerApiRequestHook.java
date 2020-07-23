package uk.co.compendiumdev.challenge;

import uk.co.compendiumdev.thingifier.api.http.HttpApiRequest;
import uk.co.compendiumdev.thingifier.api.http.HttpApiResponse;
import uk.co.compendiumdev.thingifier.apiconfig.ThingifierApiConfig;
import uk.co.compendiumdev.thingifier.application.httpapimessagehooks.HttpApiRequestHook;

public class ChallengerApiRequestHook implements HttpApiRequestHook {

    private final Challengers challengers;

    public ChallengerApiRequestHook(Challengers challengers){
        this.challengers = challengers;
    }
    @Override
    public HttpApiResponse run(final HttpApiRequest request, final ThingifierApiConfig config) {

        ChallengerAuthData challenger = challengers.getChallenger(request.getHeader("X-CHALLENGER"));
        if(challenger==null){
            // cannot track challenges
            return null;
        }

        if(request.getVerb() == HttpApiRequest.VERB.GET &&
            request.getPath().contentEquals("todos") &&
            request.getQueryParams().size()==0){
            challenger.pass(CHALLENGE.GET_TODOS);
        }

        if(request.getVerb() == HttpApiRequest.VERB.HEAD &&
                request.getPath().contentEquals("todos") &&
                request.getQueryParams().size()==0){
            challenger.pass(CHALLENGE.GET_HEAD_TODOS);
        }

        return null;
    }
}