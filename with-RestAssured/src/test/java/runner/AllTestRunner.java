package runner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import service.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestLoginService.class,
        TestProfileService.class,
        TestArticleService.class,
        TestTagsService.class,
        TestSlugService.class,
        TestCommentService.class,
        TestFavoriteService.class
})

public class AllTestRunner {}
