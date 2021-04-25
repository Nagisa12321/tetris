package observer;

/**
 * @author jtchen
 * @version 1.0
 * @date 2021/4/24 14:38
 */
public interface Observable {
	void notifyObserver();

	void registerObserver(Observer observer);
}
