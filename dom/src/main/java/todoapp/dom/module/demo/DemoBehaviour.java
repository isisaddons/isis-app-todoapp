package todoapp.dom.module.demo;

/**
* Created by Dan on 11/02/2015.
*/
public enum DemoBehaviour {
    ActivityMenuHideAll,
    ActivityMenuHideSessionLogger,
    ActivityMenuHideAuditing,
    ActivityMenuHideCommand,
    ActivityMenuHidePublishing,
    AnyExecuteAccept,
    AnyExecuteVetoWithRecoverableException,
    AnyExecuteVetoWithNonRecoverableException,
    AnyExecuteVetoWithOtherException,
    UpdateCostActionHide,
    UpdateCostActionDisable,
    UpdateCostActionInvalidate,
    DescriptionPropertyHide,
    DescriptionPropertyDisable,
    DescriptionPropertyInvalidate,
    DependenciesCollectionHide,
    // not implemented in Wicket viewer, but supported in wrapped objects
    DependenciesCollectionDisable,
    // not implemented in Wicket viewer, but supported in wrapped objects
    DependenciesCollectionInvalidateAdd,
    DependenciesCollectionInvalidateRemove,
    SimilarToCollectionHide
}
