package ru.open;


import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.util.Set;


@SupportedAnnotationTypes("ru.open.annotation.Mutator")
//@SupportedSourceVersion(SourceVersion.RELEASE_11)
//@AutoService(Processor.class)
public class MutatorCls extends AbstractProcessor {

    /*@Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (TypeElement annotation : set) {
            Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(annotation);
            for (Element annotatedElement : annotatedElements) {
                // тут будет логика обработки
            }
        }
        return true;
    }*/

    @Override
    //  получить все классы, которые помечены нашей аннотацией
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }
}
