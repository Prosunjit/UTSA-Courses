#!/usr/bin/env ruby
%w[rubygems csv time riak].each{|lib| require lib}

client  = Riak::Client.new(:host => "10.245.122.64", :http_port => 8098)
bucket  = client['whitehousedata']

quotes  = CSV.read 'whitehouse.csv'
header  = quotes.shift

quotes.each do |row|
  obj = bucket.new row.first

  puts obj.key

  obj.data = Hash[ [header, row].transpose ]
  obj.store
end
