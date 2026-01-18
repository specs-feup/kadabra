/**
 * Copyright 2016 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package weaver.kadabra.concurrent;

import java.util.concurrent.TimeUnit;

import pt.up.fe.specs.util.collections.concurrentchannel.ChannelConsumer;
import pt.up.fe.specs.util.collections.concurrentchannel.ChannelProducer;
import pt.up.fe.specs.util.collections.concurrentchannel.ConcurrentChannel;

/**
 * 
 * @author tiago
 *
 * @param <K>
 *            the Key
 * @param <V>
 *            the Value
 */
public class KadabraChannel<K, V> {

    private ConcurrentChannel<Product<K, V>> channel;
    private ChannelConsumer<Product<K, V>> consumer;
    private ChannelProducer<Product<K, V>> producer;

    private KadabraChannel(int capacity) {
        channel = new ConcurrentChannel<>(capacity);
        consumer = channel.createConsumer();
        producer = channel.createProducer();
    }

    public static <K, V> KadabraChannel<K, V> newInstance(int capacity) {
        return new KadabraChannel<>(capacity);
    }

    /**
     * Inserts the specified element into this queue if it is possible to do so immediately without violating capacity
     * restrictions, returning true upon success and false if no space is currently available. When using a
     * capacity-restricted queue, this method is generally preferable to BlockingQueue.add, which can fail to insert an
     * element only by throwing an exception.
     * 
     * 
     * @see ChannelProducer#offer(Object)
     * @return
     */
    public boolean offer(K key, V value) {
        return producer.offer(Product.newInstance(key, value));
    }

    /**
     * @see ChannelProducer#offer(Object, long, TimeUnit)
     */
    public boolean offer(K key, V value, long timeout, TimeUnit unit) throws InterruptedException {
        return producer.offer(Product.newInstance(key, value), timeout, unit);
    }

    /**
     * Retrieves and removes the head of this queue, waiting if necessary until an element becomes available.
     * 
     * @see ChannelConsumer#take()
     * @return
     */
    public Product<K, V> take() {
        try {
            return consumer.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("Problem when taking the product from the channel", e);
        }
    }

    public Product<K, V> poll() {
        return consumer.poll();

    }

    public Product<K, V> poll(long timeout, TimeUnit unit) {
        try {
            return consumer.poll(timeout, unit);
        } catch (InterruptedException e) {
            return null;
        }
    }
}
