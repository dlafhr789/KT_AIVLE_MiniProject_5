package ktaivlethminiproject.infra;

import ktaivlethminiproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class AuthorHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Author>> {

    @Override
    public EntityModel<Author> process(EntityModel<Author> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/authorapprove")
                .withRel("authorapprove")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/authordeny")
                .withRel("authordeny")
        );

        return model;
    }
}
