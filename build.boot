(set-env!
  :dependencies '[[adzerk/boot-cljs "2.1.4" :scope "test"]
                  [adzerk/boot-reload "0.5.2" :scope "test"]
                  [dynadoc "1.1.6" :scope "test"]
                  [seancorfield/boot-tools-deps "0.1.4" :scope "test"]]
  :repositories (conj (get-env :repositories)
                  ["clojars" {:url "https://clojars.org/repo/"
                              :username (System/getenv "CLOJARS_USER")
                              :password (System/getenv "CLOJARS_PASS")}]))

(require
  '[adzerk.boot-cljs :refer [cljs]]
  '[adzerk.boot-reload :refer [reload]]
  '[dynadoc.boot :refer [dynadoc]]
  '[boot-tools-deps.core :refer [deps]])

(task-options!
  pom {:project 'play-cljs
       :version "0.11.3-SNAPSHOT"
       :description "A ClojureScript game library"
       :url "https://github.com/oakes/play-cljs"
       :license {"Public Domain" "http://unlicense.org/UNLICENSE"}}
  push {:repo "clojars"})

(deftask run-docs []
  (set-env! :source-paths #{"src"} :resource-paths #{"dev-resources" "resources"})
  (comp
    (deps)
    (watch)
    (reload :asset-path "dynadoc-extend")
    (cljs)
    (dynadoc :port 5000)))

(deftask local []
  (comp (deps) (pom) (jar) (install)))

(deftask deploy []
  (comp (deps) (pom) (jar) (push)))

