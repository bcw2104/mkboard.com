#include<iostream>
#include<cmath>
using namespace std;

bool promising(int* list, int cur, int x, int y) {
	bool p = true;
	for (int i = 1; i <= cur; i++) {
		if (i == x || list[i] == y || abs(i - x) == abs(list[i] - y)) {
			p = false;
		}
	}
	return p;
}

void n_queen(int* list, int n, int& ans, int idx) {
	if (idx == n + 1) {
		ans++;
	}
	else {
		for (int i = 1; i < n + 1; i++) {
			if (promising(list, idx - 1, idx, i)) {
				list[idx] = i;
				n_queen(list, n, ans, idx + 1);
				list[idx] = 0;
			}
		}
	}
}

int main() {
	cin.tie(NULL); cout.tie(NULL);
	ios::sync_with_stdio(false);

	int n;
	int* list;
	int ans = 0;

	cin >> n;

	list = new int[n + 1]{ 0, };

	n_queen(list, n, ans, 1);

	cout << ans << "\n";
	return 0;
}