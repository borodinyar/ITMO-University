#include "graph.h"

#include <stack>
#include <string>
#include <string_view>

namespace genome {

Graph::Graph(const size_t k, const std::vector<std::string> & reads)
{
    for (const auto & read : reads) {
        const std::string_view temp(read);
        size_t ind = 0;

        while (temp.size() - k > ind) {
            add_edge(temp.substr(ind, k), temp.substr(ind + 1, k));
            ++ind;
        }
    }
}

void Graph::add_edge(const Node & first_node, const Node & second_node)
{
    graph[first_node].emplace_back(second_node);
    ++cnt_incoming_edges[second_node];
}

std::list<std::string> Graph::find_euler_path()
{
    std::stack<Node> stack;

    Node start;
    for (const auto & v : graph) {
        if (v.second.size() - cnt_incoming_edges[v.first] == 1) {
            start = v.first;
            break;
        }
    }

    std::list<std::string> nodes_on_path;

    if (!start.empty()) {
        stack.push(start);
    }

    while (!stack.empty()) {
        std::vector<Node> & curr = graph[stack.top()];

        if (!curr.empty()) {
            stack.push(std::move(curr.back()));
            curr.pop_back();
        }
        else {
            nodes_on_path.emplace_back(std::move(stack.top()));
            stack.pop();
        }
    }

    return nodes_on_path;
}

} // namespace genome