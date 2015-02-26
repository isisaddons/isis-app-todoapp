package todoapp.dom.module.categories;

import java.util.Arrays;
import java.util.List;

public enum Category {
    PROFESSIONAL {
        @Override
        public List<Subcategory> subcategories() {
            return Arrays.asList(null, Subcategory.OPEN_SOURCE, Subcategory.CONSULTING, Subcategory.EDUCATION, Subcategory.MARKETING);
        }
    }, DOMESTIC {
        @Override
        public List<Subcategory> subcategories() {
            return Arrays.asList(null, Subcategory.SHOPPING, Subcategory.HOUSEWORK, Subcategory.GARDEN, Subcategory.CHORES);
        }
    }, OTHER {
        @Override
        public List<Subcategory> subcategories() {
            return Arrays.asList(null, Subcategory.OTHER);
        }
    };

    public abstract List<Subcategory> subcategories();

    public String title() {
        return org.apache.isis.applib.util.Enums.getFriendlyNameOf(this);
    }
}
