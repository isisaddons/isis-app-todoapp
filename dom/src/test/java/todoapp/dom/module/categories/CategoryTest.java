package todoapp.dom.module.categories;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CategoryTest {

    @Test
    public void testTitle() throws Exception {
        assertThat(Category.DOMESTIC.title(), is("Domestic"));
    }

}