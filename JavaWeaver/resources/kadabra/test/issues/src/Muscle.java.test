package com.easyfitness.enums;

import android.content.res.Resources;
import com.easyfitness.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* loaded from: classes9.dex */
public enum Muscle implements Comparable<Muscle> {
    ABDOMINALS(R.string.abdominaux, 0),
    BACK(R.string.dorseaux, 1),
    BICEPS(R.string.biceps, 2),
    CALVES(R.string.mollets, 3),
    CHEST(R.string.pectoraux, 4),
    DELTOIDS(R.string.deltoids, 5),
    GLUTES(R.string.glutes, 6),
    HAMSTRINGS(R.string.ischio_jambiers, 7),
    OBLIQUES(R.string.obliques, 8),
    QUADRICEPS(R.string.quadriceps, 9),
    SHOULDERS(R.string.shoulders, 10),
    THIGHS(R.string.adducteurs, 11),
    TRAPEZIUS(R.string.trapezius, 12),
    TRICEPS(R.string.triceps, 13);
    
    private final int newId;
    private final int resourceId;

    Muscle(int i, int i2) {
        this.resourceId = i;
        this.newId = i2;
    }

    private static String bodyPartStringFor(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (Integer num : list) {
            sb.append(num.intValue());
            sb.append(";");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static Muscle fromDatabaseId(int i, Resources resources) {
        return musclesFromDatabaseIdsWithResources(resources).get(Integer.valueOf(i));
    }

    public static Muscle fromId(int i) {
        Muscle[] values;
        for (Muscle muscle : values()) {
            if (muscle.newId == i) {
                return muscle;
            }
        }
        throw new IllegalArgumentException("No muscle with that id is present");
    }

    private static Muscle fromResourceId(int i) {
        Muscle[] values;
        for (Muscle muscle : values()) {
            if (muscle.resourceId == i) {
                return muscle;
            }
        }
        throw new NoSuchElementException("No element with that resource ID could be found");
    }

    private static Map<String, Integer> localisedMuscleNames(Resources resources) {
        Muscle[] values;
        HashMap hashMap = new HashMap();
        for (Muscle muscle : values()) {
            if (muscle != GLUTES) {
                hashMap.put(resources.getString(muscle.resourceId), Integer.valueOf(muscle.resourceId));
            }
        }
        return hashMap;
    }

    private static Map<Integer, Muscle> localisedOldDatabaseIdToMuscle(List<Integer> list) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            hashMap.put(Integer.valueOf(i), fromResourceId(list.get(i).intValue()));
        }
        hashMap.put(Integer.valueOf(hashMap.size()), (Muscle) hashMap.get(Integer.valueOf(hashMap.size() - 1)));
        return hashMap;
    }

    public static String migratedBodyPartStringFor(Set<Muscle> set) {
        return set.size() == 0 ? "" : bodyPartStringFor(sortedListOfMuscleIdsFrom(set));
    }

    private static Set<Integer> muscleDatabaseIdsFromBodyPartString(String str) {
        return new HashSet<Integer>(new HashSet(Arrays.asList(str.split(";")))) { // from class: com.easyfitness.enums.Muscle.2
            final /* synthetic */ Set val$bodyParts;

            {
                this.val$bodyParts = r3;
                Iterator it = r3.iterator();
                while (it.hasNext()) {
                    add(Integer.valueOf(Integer.parseInt((String) it.next())));
                }
            }
        };
    }

    private static Map<Integer, Muscle> musclesFromDatabaseIdsWithResources(Resources resources) {
        return localisedOldDatabaseIdToMuscle(sortedLocalisedResourceIdsFromLocalisedStrings(sorted(localisedMuscleNames(resources).keySet()), resources));
    }

    public static Set<Muscle> setFromBodyParts(String str, Resources resources) {
        return str.equals("") ? new HashSet() : new HashSet<Muscle>(muscleDatabaseIdsFromBodyPartString(str), resources) { // from class: com.easyfitness.enums.Muscle.1
            final /* synthetic */ Set val$bodyPartIds;
            final /* synthetic */ Resources val$resources;

            {
                this.val$bodyPartIds = r2;
                this.val$resources = resources;
                Iterator it = r2.iterator();
                while (it.hasNext()) {
                    add(Muscle.fromDatabaseId(((Integer) it.next()).intValue(), this.val$resources));
                }
            }
        };
    }

    public static Set<Muscle> setFromMigratedBodyPartString(String str) {
        return str.equals("") ? new HashSet() : new HashSet<Muscle>(str.split(";")) { // from class: com.easyfitness.enums.Muscle.3
            final /* synthetic */ String[] val$bodyParts;

            {
                this.val$bodyParts = r5;
                for (String str2 : r5) {
                    add(Muscle.fromId(Integer.parseInt(str2)));
                }
            }
        };
    }

    private static List<String> sorted(Set<String> set) {
        ArrayList arrayList = new ArrayList(set);
        Collections.sort(arrayList);
        return arrayList;
    }

    private static List<Integer> sortedListOfMuscleIdsFrom(Set<Muscle> set) {
        ArrayList arrayList = new ArrayList(new HashSet<Integer>(set) { // from class: com.easyfitness.enums.Muscle.6
            final /* synthetic */ Set val$muscles;

            {
                this.val$muscles = set;
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    add(Integer.valueOf(((Muscle) it.next()).getNewId()));
                }
            }
        });
        Collections.sort(arrayList);
        return arrayList;
    }

    public static List<Muscle> sortedListOfMusclesUsing(final Resources resources) {
        return new ArrayList<Muscle>(new TreeMap<String, Muscle>() { // from class: com.easyfitness.enums.Muscle.4
            {
                Muscle[] values;
                for (Muscle muscle : Muscle.values()) {
                    put(muscle.nameFromResources(resources), muscle);
                }
            }
        }) { // from class: com.easyfitness.enums.Muscle.5
            final /* synthetic */ SortedMap val$muscleNames;

            {
                this.val$muscleNames = r3;
                for (String str : r3.keySet()) {
                    add((Muscle) this.val$muscleNames.get(str));
                }
            }
        };
    }

    private static List<Integer> sortedLocalisedResourceIdsFromLocalisedStrings(List<String> list, Resources resources) {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            arrayList.add(localisedMuscleNames(resources).get(str));
        }
        return arrayList;
    }

    public int getNewId() {
        return this.newId;
    }

    public String nameFromResources(Resources resources) {
        return resources.getString(this.resourceId);
    }
}
