package org.ia;

import static org.common.Constant.Prompt.DART_COMMENTAIRE_CONTEXT;
import static org.common.Constant.Prompt.DART_COMMENTATEUR_INIT;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface CommentateurService {

    // Scope / context passed to the LLM
  @SystemMessage(DART_COMMENTATEUR_INIT)
  // Prompt (with detailed instructions and variable section) passed to the LLM
  @UserMessage(DART_COMMENTAIRE_CONTEXT)
  String commentVolee(String question);

}
