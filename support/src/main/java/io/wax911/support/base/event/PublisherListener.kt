package io.wax911.support.base.event

/**
 * Created by max on 2017/06/18.
 * Planned use will involve firing events other classes via publisher/subscriber pattern
 * @see org.greenrobot.eventbus.EventBus
 */

interface PublisherListener<T> {

    /**
     * Responds to published events, be sure to add subscribe annotation
     * @see org.greenrobot.eventbus.Subscribe
     *
     *
     * @param param passed event
     */
    fun onEventPublished(param: T)
}
