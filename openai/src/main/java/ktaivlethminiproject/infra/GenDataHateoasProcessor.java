package ktaivlethminiproject.infra;

import ktaivlethminiproject.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class GenDataHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<GenData>> {

    @Override
    public EntityModel<GenData> process(EntityModel<GenData> model) {
        return model;
    }
}
