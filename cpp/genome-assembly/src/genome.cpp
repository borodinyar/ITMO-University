#include "genome.h"

#include "graph.h"

namespace genome {

std::string get_string(std::list<std::string> & path)
{
    std::string ans = std::move(path.back());
    path.pop_back();

    while (!path.empty()) {
        ans += path.back().back();
        path.pop_back();
    }

    return ans;
}

std::string assembly(const size_t k, const std::vector<std::string> & reads)
{
    if (k == 0 || reads.empty())
        return "";

    Graph g(k, reads);

    std::list<std::string> path = g.find_euler_path();

    return get_string(path);
}

} // namespace genome
