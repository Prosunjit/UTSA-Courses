#!/usr/bin/env ruby
%w[rubygems riak].each{|lib| require lib}

client  = Riak::Client.new(:host => "IP-ADDR-MASTER", :http_port => 8098)

results = Riak::MapReduce.new(client).
                add("goog").
                map("Riak.mapValuesJson", :keep => true).run

p results
