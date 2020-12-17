import java.util.*;

public class StringChallengeTree {
    /*
    The given string has pipe delimited nodes that represent family members in a family tree. Each node is a CSV with the values being "parent_id, node_id, node_name". Write a method that takes an input string and return a single result that represents the data as a hierarchy (root, children, siblings, etc).
Sample input: "null,0,grandpa|0,1,son|0,2,daugther|1,3,grandkid|1,4,grandkid|2,5,grandkid|5,6,greatgrandkid"
• Solve it in any language that you prefer
• Display the hierarchical result any way you prefer (as long as the parent/child connections are clear)
     */

    private String id;
    private String parentId;
    private String name;
    private List<StringChallengeTree> children = new ArrayList<>();

    @Override
    public String toString() {
        return name + " (id: " + id + " pid: " + parentId + ")";
    }

    public String printFamily(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) sb.append("-");
        sb.append(this.toString() + "\n");
        for (StringChallengeTree child : children) {
            sb.append(child.printFamily(level + 1));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String input = "null,0,grandpa|0,1,son|0,2,daugther|1,3,grandkid|1,4,grandkid|2,5,grandkid|5,6,greatgrandkid";
        System.out.println("string input: " + input);

        StringTokenizer stringTokenizer = new StringTokenizer(input, "|");

        List<StringChallengeTree> familyList = new ArrayList<>();
        Map<String, StringChallengeTree> familyMap = new HashMap<>();

        StringChallengeTree root = null;

        //tokenize into data structures and find root
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            StringTokenizer stringSubTokenizer = new StringTokenizer(token, ",");
            StringChallengeTree stringChallengeTree = new StringChallengeTree();
            stringChallengeTree.parentId = stringSubTokenizer.nextToken();
            stringChallengeTree.id = stringSubTokenizer.nextToken();
            stringChallengeTree.name = stringSubTokenizer.nextToken();
            familyList.add(stringChallengeTree);
            familyMap.put(stringChallengeTree.id, stringChallengeTree);
            if (stringChallengeTree.parentId.equals("null")) {
                root = stringChallengeTree;
            }
        }
        System.out.println("tokenized family list: " + familyList);
        //connect family tree
        for (StringChallengeTree familyMember : familyList) {
            if (familyMember.parentId.equals("null")) continue;
            StringChallengeTree parent = familyMap.get(familyMember.parentId);
            parent.children.add(familyMember);
        }
        System.out.println("connected family map: " + familyMap);

        //print family tree from root
        System.out.println("family tree:\n" + root.printFamily(0));

/* output
family tree:
grandpa (id: 0 pid: null)
-son (id: 1 pid: 0)
--grandkid (id: 3 pid: 1)
--grandkid (id: 4 pid: 1)
-daugther (id: 2 pid: 0)
--grandkid (id: 5 pid: 2)
---greatgrandkid (id: 6 pid: 5)

*/
    }
}
