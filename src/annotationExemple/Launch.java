package annotationExemple;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;



public class Launch {
	
	public static void main(String[] args) {
		Personne person = new Personne("Hey");
		
		/*
	 	// Avoir les annotations de la classe
		for(Annotation a : person.getClass().getAnnotations()) {
			System.out.println(a);
		}*/
		
		// Avoir toutes les méthodes qui ont une annotations UserActions
		for(Method method : person.getClass().getMethods()) {
			if(method.isAnnotationPresent(UserActions.class)) {
				System.out.println(method.getName());

			};
			for(Annotation annotation : method.getAnnotationsByType(UserActions.class)) {
				System.out.println(annotation);
			}
		}
		
		/*
		List.of(person.getClass().getMethods()).forEach(item -> {
			System.out.println(item.getName());
			List.of(item.getDeclaredAnnotations()).forEach(item2 -> {
				System.out.println(item2.getClass());
			});
			System.out.println("---");
		});
		*/
	}

	
}
