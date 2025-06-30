package ktaivlethminiproject.infra;

import ktaivlethminiproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class SubscribeHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Subscribe>> {

    @Override
    public EntityModel<Subscribe> process(EntityModel<Subscribe> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/borrowbook")
                .withRel("borrowbook")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/ownbook")
                .withRel("ownbook")
        );

        return model;
    }
}
