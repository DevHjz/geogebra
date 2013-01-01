/*
Archimedean 1.1, a 3D applet/application for visualizing, building, 
transforming and analyzing Archimedean solids and their derivatives.
Copyright 1998, 2011 Raffi J. Kasparian, www.raffikasparian.com.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.quantimegroup.solutions.archimedean.utils;import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
public class ObjectList<E> extends AbstractList<E> {	public Object objects[] = null;	public int num = 0;	private int growBy = 0;	// boolean canStretch = false;	public ObjectList() {		this(0, 0);	}	public ObjectList(int size) {		this(size, 0);	}	public ObjectList(int size, int growBy) {		this.growBy = growBy;		objects = new Object[size];	}	public ObjectList(E[] theObjects) {		this(theObjects, theObjects.length);	}	public ObjectList(E[] theObjects, int length) {		this(length, 0);		System.arraycopy(theObjects, 0, objects, 0, length);		num = length;	}		public int capacity(){		return objects.length;	}	/*	 * ObjectList( int size, boolean canStretch ){ this.canStretch = canStretch;	 * objects = new Object[size]; }	 */	public int addReturnIndex(E o) {		if (num >= objects.length && growBy > 0) {			Object[] temp = new Object[objects.length + growBy];			System.arraycopy(objects, 0, temp, 0, num);			objects = temp;		}		objects[num] = o;		++num;		return num - 1;	}	public boolean add(E o) {		if (num >= objects.length && growBy > 0) {			Object[] temp = new Object[objects.length + growBy];			System.arraycopy(objects, 0, temp, 0, num);			objects = temp;		}		objects[num] = o;		++num;		return true;	}	public void setSize(int s) {		if(s > objects.length){			Object[] temp = new Object[s];			System.arraycopy(objects, 0, temp, 0, num);			objects = temp;		}		num = s;	}	void insert(int i, E o) {		System.arraycopy(objects, i, objects, i + 1, num - i);		objects[i] = o;		++num;	}	public boolean remove(Object o) {		int index = find((E) o);		if (index != -1) {			removeIndex(index);		}		return index != -1;	}	public void removeIndex(int i) {		--num;		objects[i] = objects[num];	}	void removeIndex(int first, int last) {		System.arraycopy(objects, last + 1, objects, first, num - last - 1);		num -= last - first + 1;	}	void orderedRemove(int i) {		if (i > 0 && i < num - 1) {			System.arraycopy(objects, i + 1, objects, i, num - i - 1);		}		--num;	}	public E get(int i) {		return (E) objects[i];	}	public E wrapget(int i) {		return get(indexToRange(i));	}	public E getLast() {		return (E) objects[num - 1];	}	public E set(int i, E o) {		E current = (E) objects[i];		objects[i] = o;		return current;	}	public int find(E o) {		for (int i = 0; i < num; ++i) {			if (objects[i] == o)				return i;		}		return -1;	}	int findAfterIndex(E o, int index) {		for (int i = index + 1; i < num; ++i) {			if (objects[i] == o)				return i;		}		return -1;	}	public ObjectList<E> copy() {		ObjectList<E> ol = new ObjectList<E>(objects.length);		System.arraycopy(objects, 0, ol.objects, 0, num);		ol.num = num;		return ol;	}	ObjectList<E> shrinkCopy() {		ObjectList<E> ol = new ObjectList<E>(num);		System.arraycopy(objects, 0, ol.objects, 0, num);		ol.num = num;		return ol;	}	public ObjectList<E> wrapCopy(int start, int inc) {		ObjectList<E> ol = new ObjectList<E>(objects.length);		for (int i = 0, j = start; i < num; ++i, j += inc) {			ol.objects[i] = wrapget(j);		}		ol.num = num;		return ol;	}	public void shrink() {		if (objects.length > num) {			Object[] temp = new Object[num];			System.arraycopy(objects, 0, temp, 0, num);			objects = temp;		}	}	int indexToRange(int i) {		while (i < 0)			i += num;		return i % num;	}	int length() {		return objects.length;	}	int removeDuplicates() {		int count = 0;		for (int i = 0; i < num; ++i) {			for (int j = i + 1; j < num; ++j) {				if (objects[j] == objects[i]) {					removeIndex(j);					--j;					++count;				}			}		}		return count;	}	public String toString() {		StringBuffer buf = new StringBuffer("(");		for (int i = 0; i < num; ++i) {			buf.append(String.valueOf(objects[i]));			if (i < num - 1) {				buf.append(", ");			}		}		buf.append(")");		return buf.toString();	}	public Iterator<E> iterator() {		return Arrays.asList((E[]) shrinkCopy().objects).iterator();	}	@Override	public int size() {		return num;	}	public void clear() {		num = 0;	}}