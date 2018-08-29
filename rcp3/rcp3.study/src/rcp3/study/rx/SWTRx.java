package rcp3.study.rx;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Wrap SWT event to the Observable of RxJava.
 *
 * @author alzhang
 */
public class SWTRx {

  private static PublishSubject<Event> SUBJECT = PublishSubject.create();

  /**
   * Add listener to a SWT Widget.
   *
   * @param widget a Widget.
   * @param eventType SWT event type.
   * @return an Observable of SWT Event.
   */
  public static Observable<Event> addListener(Widget widget, int eventType) {
    Listener listener = new Listener() {
      @Override
      public void handleEvent(Event event) {
        SUBJECT.onNext(event);
      }
    };
    widget.addListener(eventType, listener);

    Observable<Event> observableEvents = SUBJECT.ofType(Event.class)
        .filter(evt -> evt.widget == widget && evt.type == eventType);

    return observableEvents;
  }

  public static void send(Event event) {
    SUBJECT.onNext(event);
  }

  public static void subscribe(Widget widget, int eventType, Consumer<? super Event> onNext) {
    widget.addListener(eventType, SUBJECT::onNext);

    Disposable disposable = SUBJECT.ofType(Event.class)
        .filter(evt -> evt.widget == widget && evt.type == eventType)
        .subscribe(onNext);

    widget.addDisposeListener(evt -> disposable.dispose());
  }

  public static void subscribe(Widget widget, int eventType, Consumer<? extends Event> onNext, Consumer<? super Throwable> onError) {

  }

}
