package pl.edu.wit.calculators.salary;

import org.jline.utils.AttributedString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class ShellConfiguration {

    @Bean
    public PromptProvider calculatorPrompt() {
        return () -> new AttributedString("calculator:>");
//        return new PromptProvider() {
//            @Override
//            public AttributedString getPrompt() {
//                return new AttributedString("calculator:>");
//            }
//        };
    }
}
