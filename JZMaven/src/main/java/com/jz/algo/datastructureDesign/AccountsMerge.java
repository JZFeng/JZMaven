/**
 * @Author jzfeng
 * @Date 3/30/22-10:36 AM
 */

package com.jz.algo.datastructureDesign;

import java.util.*;

/**
 [721. Accounts Merge](https://leetcode.com/problems/accounts-merge/)
 */
class AccountsMerge {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        //建立一个 根据邮箱地址到Index的映射
        Map<String, Integer> emailToIndex = new HashMap<String, Integer>();
        //简历一个 根据邮箱地址到户主名字的映射
        Map<String, String> emailToName = new HashMap<String, String>();
        //统计邮箱的个数
        int emailsCount = 0;
        //遍历给出的用户和对应的邮箱地址
        for (List<String> account : accounts) {
            //得到当前遍历账户下的户主名字
            String name = account.get(0);
            //得到当前遍历账户下List的长度
            int size = account.size();
            //通过此长度对接下来的邮箱进行遍历
            for (int i = 1; i < size; i++) {
                //得到当前i时的 邮箱地址
                String email = account.get(i);
                //看邮箱对应Index中是否有这个邮箱地址，保证每个邮箱的唯一性
                if (!emailToIndex.containsKey(email)) {
                    //如果没有出现过这个邮箱，将它添加到两个对应的map中
                    emailToIndex.put(email, emailsCount++);
                    emailToName.put(email, name);
                }
            }
        }
        //此时两个Map中分别存储了每个邮箱以及它对应的Index，每个邮箱它对应的户主名字。接下来建立并查集
        UnionFind uf = new UnionFind(emailsCount);
        //遍历account
        for (List<String> account : accounts) {
            //得到当前account下第一个邮箱的地址，根据地址得到索引，把它作为根
            String firstEmail = account.get(1);
            int firstIndex = emailToIndex.get(firstEmail);
            //得到当前account下的邮箱个数进行遍历
            int size = account.size();
            for (int i = 2; i < size; i++) {
                //得到需要进行合并操作的邮箱地址，因为对于同一账户，他们都属于一个人，因此把他们合并
                String nextEmail = account.get(i);
                //根据邮箱地址从映射中取出Index
                int nextIndex = emailToIndex.get(nextEmail);
                //合并操作
                uf.union(firstIndex, nextIndex);
            }
        }
        //通过上面的操作，首先将同一个账户中的每个邮箱指向根邮箱，后续
        //在其他的账户中遇到了相同的邮箱，在接着合并操作，此时对应根节点的个数就是总用户数

        //建立一个Index对应邮箱地址的Map
        Map<Integer, List<String>> indexToEmails = new HashMap<Integer, List<String>>();
        //通过emailToIndex遍历邮箱地址
        for (String email : emailToIndex.keySet()) {
            //根据索引在并查集中得到当前邮箱地址的根节点
            int index = uf.find(emailToIndex.get(email));
            //判断是否存在根节点，没有就创建一个ArrrayList，有的话就获取当前的ArrayList
            List<String> account = indexToEmails.getOrDefault(index, new ArrayList<String>());
            //在当前的List中加入现邮箱地址，因为他们有同一个根
            account.add(email);
            //更新indexToEmails的邮箱列表
            indexToEmails.put(index, account);
        }
        //创建答案表，按要求对答案进行整理
        List<List<String>> merged = new ArrayList<List<String>>();
        //通过indexToEmail的值（values）进行遍历
        for (List<String> emails : indexToEmails.values()) {
            //将emails按照题目要求进行排序
            Collections.sort(emails);
            //得到当前邮箱组的根，然后根据根得到用户名字
            String name = emailToName.get(emails.get(0));
            //创建一个account表
            List<String> account = new ArrayList<String>();
            //首先添加用户名字，然后用addAll将emails表整个添加到account中
            account.add(name);
            account.addAll(emails);
            //最后在答案表中添加整理好后的用户以及邮箱表
            merged.add(account);
        }
        return merged;
    }

    class UnionFind {

        private int[] father = null;
        private int count;

        public UnionFind(int n) {
            // initialize your data structure here.
            father = new int[n];
            for (int i = 0; i < n; ++i) {
                father[i] = i;
            }
        }

        private int find(int x) {
            if (father[x] == x) {
                return x;
            }
            return father[x] = find(father[x]);
        }


        public void union(int a, int b) {
            int root_a = find(a);
            int root_b = find(b);
            if (root_a != root_b) {
                father[root_a] = root_b;
                count--;
            }
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}

