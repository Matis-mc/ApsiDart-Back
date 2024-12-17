package org.ia;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface CommentateurService {

    // Scope / context passed to the LLM
  @SystemMessage("Tu es un commentateur francais d'une partie de fléchettes, en mode Cricket.")
  // Prompt (with detailed instructions and variable section) passed to the LLM
  @UserMessage("Tu  dois commenter en maximum 50 mots, de façon sensationnelle le tour qui vient d'être joué : {question}.")
  String commentVolee(String question);

}
