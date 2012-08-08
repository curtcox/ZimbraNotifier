package zimbra;

import java.util.Iterator;
import login.ILoginDialog;
import mapper.*;
import notify.*;
import prefs.Preferences;
import strings.ExceptionTransformStringSource;
import strings.ForkedString;
import threads.Threads;
import util.All;

/**
 * Polls a Zimbra inbox and briefly displays a notification window when contents change.
 * @author curt
        this.toaster = toaster;
 */
final class AsolutionsZimbraNotifierApp {

    final ILoginDialog login;
    final Iterable<Boolean> every_N_seconds;
    final ChangeDetector change_detector;
    final StringMapper formatted_page;
    final StringMapper only_new_formatted_page;
    final Notifiable toaster;
    final Iterator<ForkedString<ZimbraEmail>> messages;
    
    private AsolutionsZimbraNotifierApp(
            ILoginDialog dialog, Notifiable toaster, Iterator<ForkedString<ZimbraEmail>> inboxFeed, int everyNSeconds)
    {
        every_N_seconds = All.everyNSeconds(everyNSeconds);
        this.login = dialog;
        this.toaster = toaster;
        messages = messages(inboxFeed);
        StringMapper page = PageWrapper.of(dialog);
        StringMapper emailToHtml = ZimbraEmailToHtml.of();
        StringMapper last_25_unique = whenEmail(
                series(
                    ZimbraEmailTimeSorter.of(),
                    UniqueMapper.of(),
                    TopStringMapper.of(25)
                ));
        change_detector = ZimbraChangeDetector.of(
                PartitioningChangeDetector.of(exception(last_25_unique))
        );
        
        formatted_page = series (
            whenEmail(series( last_25_unique, emailToHtml )),
            page
        );
        
        only_new_formatted_page = series(
            whenEmail(
                series( last_25_unique, OnlyNewZimbraEmails.of(), emailToHtml )
            ),
            page
        );
    }
    
    static AsolutionsZimbraNotifierApp of(
        ILoginDialog dialog, Notifiable toaster,Iterator<ForkedString<ZimbraEmail>> inboxFeed,int everyNSeconds) {
        return new AsolutionsZimbraNotifierApp(dialog,toaster,inboxFeed,everyNSeconds);
    }

    void login() {
        login.refreshCredentials();
        if (login.getUser().isEmpty()) {
            System.exit(0);
        }
    }
    
    Iterator<ForkedString<ZimbraEmail>> messages(Iterator<ForkedString<ZimbraEmail>> messages) {
        Iterator messages1 = messages;
        Iterator messages2 = ZimbraExceptionTransformStringSource.of(login,messages1); 
        Iterator messages3 = ExceptionTransformStringSource.of(messages2);
        Iterator messages4 = NotifiableTee.to(messages3,login);
        return messages4;
    }

    Notifiable notifiable() {
        Preferences.Adapter prefs = Preferences.Adapter.of(login); 
        return all(
                   when(prefs.isBeepEnabled(), NotifiableBeep.of()),
                   when(prefs.showWindowOnNewMail(), toaster)
               );
    }

    void showToasterOnDialogIconified() {
        login.registerForNotification(new Notifiable() {
            @Override public void deliverNotification(ForkedString notification) {
                final ForkedString message = refreshPage();
                Threads.runOnEDT(new Runnable() { @Override public void run() {
                    toaster.deliverNotification(message);
                }});
            }});
    }

    ForkedString refreshPage() {
        return formatted_page.transform(messages.next());
    }
    
    void startMonitoring() {
        messages.startMonitoring(configureTimedNotifier());
    }

    TimedNotifier.Builder configureTimedNotifier() {
        Iterator messages = this.messages;
        return TimedNotifier.builder()
            .running(every_N_seconds)
            .onChangeNotify(notifiable())
            .detector(change_detector)
            .transformer(only_new_formatted_page)
            .messages(messages);
    }
    
    static Notifiable all(Notifiable... notifiable) {
        return NotifiableComposite.of(notifiable);    
    }
    
    static Notifiable when(Iterator<Boolean> booleans, Notifiable notifiable) {
        return NotifiableConditional.of(notifiable, booleans);
    }

    static StringMapper exception(StringMapper mapper) {
        return ExceptionTransformStringMapper.of(mapper);
    }
    
    static StringMapper series(StringMapper... mappers) {
        return StringMapperSeries.of(mappers);
    }
    
    static StringMapper whenEmail(StringMapper mapper) {
        return TypeMapper.of(ZimbraEmail.class, mapper);
    }

}
