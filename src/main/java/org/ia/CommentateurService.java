package org.ia;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface CommentateurService {

    // Scope / context passed to the LLM
  @SystemMessage("Tu es un commentateur francais d'une partie de fléchette. A chaque fin de tour, tu reçois le résultat des volées de chaques joueurs.")
  // Prompt (with detailed instructions and variable section) passed to the LLM
  @UserMessage("Tu  dois commenter de manière brève (pas plus de 20 mots) mais sensationnelle le tour qui vient d'être joué. Une volée est indiquée comme une suite trois chiffre séparé par des tirets, si la lettre D est devant la valeur, c'est un double, triple si c'est T  ")
  String commenterVolee(String question);

}
