#pragma once

#include <list>
#include <map>
#include <string_view>
#include <unordered_map>
#include <vector>

namespace genome {

using Node = std::string_view;

class Graph
{
public:
    Graph(std::size_t k, const std::vector<std::string> & reads);

    void add_edge(const Node & first_node, const Node & second_node);

    std::list<std::string> find_euler_path();

private:
    std::unordered_map<Node, std::vector<Node>> graph;
    std::unordered_map<Node, unsigned> cnt_incoming_edges;
};
} // namespace genome