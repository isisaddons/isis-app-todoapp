package todoapp.integtests.assertions;

import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;


/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
public class Assertions {

  /**
   * Creates a new instance of <code>{@link org.apache.isis.applib.services.eventbus.PropertyDomainEventAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static <S, T> PropertyDomainEventAssert<S, T> assertThat(PropertyDomainEvent<S, T> actual) {
    return new PropertyDomainEventAssert<S, T>(actual);
  }

  /**
   * Creates a new instance of <code>{@link todoapp.dom.module.todoitem.ToDoItemAssert}</code>.
   *
   * @param actual the actual value.
   * @return the created assertion object.
   */
  public static ToDoItemAssert assertThat(todoapp.dom.module.todoitem.ToDoItem actual) {
    return new ToDoItemAssert(actual);
  }

  /**
   * Creates a new <code>{@link Assertions}</code>.
   */
  protected Assertions() {
    // empty
  }
}
