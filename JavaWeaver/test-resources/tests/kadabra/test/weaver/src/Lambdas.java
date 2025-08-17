/*
import java.util.Iterator;
import org.apache.tools.ant.ProjectHelper;
import java.util.stream.Stream;
import java.lang.reflect.Constructor;
import org.apache.tools.ant.ProjectHelperRepository;
import java.lang.Exception;
import org.apache.tools.ant.BuildException;



public class Lambdas {

	public Iterator<ProjectHelper> getHelpers() {
        Stream.Builder<Constructor<? extends ProjectHelper>> b = Stream.builder();
        helpers.forEach(b::add);
        return b.add(ProjectHelperRepository.PROJECTHELPER2_CONSTRUCTOR).build().map(c -> {
            try {
                return c.newInstance();
            } catch (Exception e) {
                throw new BuildException("Failed to invoke no-arg constructor"
                        + " on " + c.getName());
            }
        }).map(ProjectHelper.class::cast).iterator();
    }  
	
}
*/
/*
import java.util.Iterator;
import org.apache.tools.ant.ProjectHelper;
import java.util.stream.Stream;
import java.lang.reflect.Constructor;
import org.apache.tools.ant.ProjectHelperRepository;
import java.lang.Exception;
import org.apache.tools.ant.BuildException;
import java.lang.String;


public class Lambdas {

	public Iterator<Integer> getHelpers() {
        Stream.Builder<Constructor<String>> b = Stream.builder();
        //helpers.forEach(b::add);
        return b.map(c -> {
            try {
                return 10;
            } catch (Exception e) {
                throw new BuildException("Failed to invoke no-arg constructor"
                        + " on " + c.getName());
            }
        }).map(ProjectHelper.class::cast).iterator();
    }  
	
}
*/

/*
import java.util.function.Supplier;
import java.lang.Exception;
import java.lang.RuntimeException;
import org.apache.tools.ant.BuildException;

public class Lambdas {

	public static Integer tryCatchInLambda() {
		Supplier<Integer> supplier = () -> {
			try {
				return 10;
			} catch (Exception e) {
				//throw new RuntimeException("");
				throw new BuildException("");				
			}
		};
		
		return supplier.get();
	}
}
*/



import java.util.Iterator;
import org.apache.tools.ant.ProjectHelper;
import java.util.stream.Stream;
import java.lang.reflect.Constructor;
import org.apache.tools.ant.ProjectHelperRepository;
import java.lang.Exception;
import java.lang.RuntimeException;
import org.apache.tools.ant.BuildException;
import java.util.function.Function;


public class Lambdas {

	public Lambdas() {
	}

	public void getHelpers() {
        Function<Integer, Integer> a = c -> {
            try {
                return c + 10;
            } catch (Exception e) {
                // Nothing
            }
        });
        
    }  
    
   	public Function<Integer, Integet> foo() {
		return a -> a + 10;   	
	}
}
